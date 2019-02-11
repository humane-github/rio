#!/usr/bin/env python
# -*- coding: utf-8 -*

from __future__ import print_function
import cv2
import requests

png_file = 'tryAuth.png'


def communicate(img, camera_Id, url):
    # imgをpngに変換
    cv2.imwrite(png_file, img)

    im = open(png_file, 'rb')

    files = {'image': (png_file, im, 'image/png')}
    data = { 'cameraId': camera_Id}

    # リクエスト実行
    r = requests.post(url,
                      files = files,
                      data = data
                      )

    result = True

    if r.status_code != 200:
        result = False
    else:
        j = r.json()

        if j.get("resultInfo") != True:
            result = False

    return result