#!/usr/bin/env python
# -*- coding: utf-8 -*

from __future__ import print_function
import cv2


def detect(img, cascade):
    rects = cascade.detectMultiScale(img,
                                     scaleFactor = 1.3,
                                     minNeighbors = 4,
                                     minSize = (30, 30),
                                     flags = cv2.CASCADE_SCALE_IMAGE)

    if len(rects) == 0:
        return []

    rects[:, 2:] += rects[:,:2]
    return rects

