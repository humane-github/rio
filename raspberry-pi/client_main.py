#!/usr/bin/env python
# -*- coding: utf-8 -*

# Python 2/3 compatibility
from __future__ import print_function

import numpy as np
import cv2

import RPi.GPIO as GPIO

import face_detect as fd
import server_communicate as sc

# local modules
from video import create_capture
from common import clock, draw_str

def draw_rects(img, rects, color):
    for x1, y1, x2, y2 in rects:
        cv2.rectangle(img, (x1, y1), (x2, y2), color, 2)


if __name__ == '__main__':
    import sys, getopt
    print(__doc__)

    args, video_src = getopt.getopt(sys.argv[1:], '', ['cascade=', 'nested-cascade='])
    try:
        video_src = video_src[0]
    except:
        video_src = 0
    args = dict(args)

    cascade_fn = args.get('--cascade', "/home/pi/opencv-3.1.0/data/haarcascades/haarcascade_frontalface_alt.xml")
#    nested_fn  = args.get('--nested-cascade', "/home/pi/opencv-3.1.0/data/haarcascades/haarcascade_eye.xml")

    cascade = cv2.CascadeClassifier(cascade_fn)
#    nested = cv2.CascadeClassifier(nested_fn)

    cam = create_capture(video_src, fallback='synth:bg=/home/pi/opencv-3.1.0/data/lena.jpg:noise=0.05')

    GPIO.setmode(GPIO.BCM)
    GPIO.setup(25, GPIO.OUT)

    while True:
        ret, img = cam.read()
        gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        gray = cv2.equalizeHist(gray)

        t = clock()
        rects = fd.detect(gray, cascade)
        vis = img.copy()
        draw_rects(vis, rects, (0, 255, 0))

        #dt = clock() - t
        #draw_str(vis, (20, 20), 'time: %.1f ms' % (dt * 1000))
        cv2.imshow('facedetect', vis)


        #GPIO.output(25, GPIO.HIGH)

        print(rects)

        # 検出した場合
        if len(rects) > 0:
            result = sc.communicate(img, '1', 'http://192.168.1.8:80/rio/mng/room-watch/v1.0/user/certify')

            print(result)

            if result == True:
                # ロック解除
                GPIO.output(25, GPIO.HIGH)
            else:
                # ロック
                GPIO.output(25, GPIO.LOW)
        else:
            GPIO.output(25, GPIO.LOW)

        # 消灯
        #GPIO.cleanup()



        if 0xFF & cv2.waitKey(5) == 27:
            break

    cv2.destroyAllWindows()