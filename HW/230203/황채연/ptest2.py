import cv2
from PIL import Image, ImageFont, ImageDraw
import numpy as np
import time

def PIL2CV(pil_image):
    numpy_image= np.array(pil_image)
    opencv_image = cv2.cvtColor(numpy_image, cv2.COLOR_RGB2BGR)
    return opencv_image

def CV2PIL(opencv_image):
    color_coverted = cv2.cvtColor(opencv_image, cv2.COLOR_BGR2RGB)
    pil_image = Image.fromarray(color_coverted)
    return pil_image

img = cv2.imread('example.jpg', cv2.IMREAD_UNCHANGED)
pil_image = CV2PIL(img)

fontpath = "gulim.ttc"
font = ImageFont.truetype(fontpath, 24)
b,g,r,a = 0,0,255,255
draw = ImageDraw.Draw(pil_image, 'RGBA')
draw.text((200, 70), "텍스트를 삽입합니다.℃", font=font, fill=(b,g,r,a))

cv2_image = PIL2CV(pil_image)
cv2.imshow("asd", cv2_image)
cv2.waitKey()
