#coding:utf-8
import cv2
from ultralytics import YOLO
import detect_tools as tools
from PIL import ImageFont
# from paddleocr import PaddleOCR
import pymysql
import requests
import json


def insert_parking_info(car_number, entry_time, fee_id):
    # API URL，注意修正了端口和路径以匹配问题描述
    url = "http://localhost:8081/parking/parkingInfo/insert"

    # 准备要发送的数据
    new_parking_info = {
        "carNumber": car_number,
        "entryTime": entry_time,
        "feeId": fee_id
        # 可以根据需要在此处添加更多字段
    }

    try:
        # 发送POST请求

        response = requests.post(url, data=new_parking_info)

        # 检查响应状态码
        if response.status_code == 200:
            # 尝试解析响应内容，假设返回的是JSON格式
            result = response.json()
            print("操作成功，响应  数据:", json.dumps(result, indent=4))
            return result
        else:
            # 打印错误信息并返回None表示操作失败
            print(f"操作失败，状态码: {response.status_code}, 错误信息: {response.text}")
            return None
    except requests.exceptions.RequestException as e:
        # 处理请求异常，如网络问题
        print(f"请求过程中发生错误: {e}")
        return None


def departure_(car_number, entry_time, fee_id):
    # API URL，注意修正了端口和路径以匹配问题描述
    url = "http://localhost:8081/parking/parkingInfo/departure"

    # 准备要发送的数据
    new_parking_info = {
        "carNumber": car_number,
        "entryTime": entry_time,
        "feeId": fee_id
        # 可以根据需要在此处添加更多字段
    }

    try:
        # 发送POST请求

        response = requests.post(url, data=new_parking_info)

        # 检查响应状态码
        if response.status_code == 200:
            # 尝试解析响应内容，假设返回的是JSON格式
            result = response.json()
            print("操作成功，响应  数据:", json.dumps(result, indent=4))
            return result
        else:
            # 打印错误信息并返回None表示操作失败
            print(f"操作失败，状态码: {response.status_code}, 错误信息: {response.text}")
            return None
    except requests.exceptions.RequestException as e:
        # 处理请求异常，如网络问题
        print(f"请求过程中发生错误: {e}")
        return None
departure_("苏A12345", "2023-05-01 12:00:00", 1)
# def get_license_result(ocr,image):
#     """
#     image:输入的车牌截取照片
#     输出，车牌号与置信度
#     """
#     #将图像放大两倍
#     image = cv2.resize(image, (image.shape[1] * 3, image.shape[0] * 3), interpolation=cv2.INTER_CUBIC)
#     cv2.imshow('image',image)
#
#     result = ocr.ocr(image, cls=False)[0]
#     if result:
#         license_name, conf = result[0][1]
#         if '·' in license_name:
#             license_name = license_name.replace('·', '')
#         return license_name, conf
#     else:
#         return None, None
#
#
#
#
# fontC = ImageFont.truetype("Font/platech.ttf", 50, 0)
# # 加载ocr模型
# cls_model_dir = 'paddleModels/whl/cls/ch_ppocr_mobile_v2.0_cls_infer'
# rec_model_dir = 'paddleModels/whl/rec/ch_PP-OCRv3_rec_infer'
# ocr = PaddleOCR(use_angle_cls=False, lang="ch", det=False, cls_model_dir=cls_model_dir,rec_model_dir=rec_model_dir)
#
# # 所需加载的模型目录
# path = 'flip_v10np.pt'
# # 加载预训练模型
# # conf	0.25	object confidence threshold for detection
# # iou	0.7	intersection over union (IoU) threshold for NMS
# model = YOLO(path, task='detect')
#
# ID = 0
#
# while(ID<10):
#     cap = cv2.VideoCapture(ID)
#     # get a frame
#     ret, frame = cap.read()
#     if ret == False:
#         ID += 1
#     else:
#         print('摄像头ID:',ID)
#         break
# plate_count = {}
# frame_counter = 0
# # Loop through the video frames
# while cap.isOpened():
#     # Read a frame from the video
#     success, frame = cap.read()
#
#     if success:
#         # Run YOLOv10 inference on the frame
#         results = model(frame)[0]
#         class_ids = results.boxes.cls.tolist()
#         class_names = ['green','blue']  # 这里填充实际的类别名称列表
#         # 将类别ID转换为类别名称
#         class_list = [class_names[int(cls_id)] for cls_id in class_ids]
#         print(class_list)
#         location_list = results.boxes.xyxy.tolist()
#         if len(location_list) >= 1:
#             location_list = [list(map(int, e)) for e in location_list]
#             # 截取每个车牌区域的照片
#             license_imgs = []
#             for each in location_list:
#                 x1, y1, x2, y2 = each
#                 cropImg = frame[y1:y2, x1:x2]
#                 license_imgs.append(cropImg)
#             # 车牌识别结果
#             lisence_res = []
#             conf_list = []
#             # for each in license_imgs:
#             #     license_num, conf = get_license_result(ocr, each)
#             #
#             #     if license_num:
#             #         if '-' in license_num:
#             #             license_name = license_num.replace('-', '')
#             #         lisence_res.append(license_num)
#             #         conf_list.append(conf)
#             #     else:
#             #         lisence_res.append('无法识别')
#             #         conf_list.append(0)
#             # for text, box in zip(lisence_res, location_list):
#             #     frame = tools.drawRectBox(frame, box, text, fontC)
#             # 遍历许可证图片列表，对每张图片进行车牌识别
#             for each in license_imgs:
#                 # 调用OCR技术对当前图片进行车牌识别，返回车牌号和识别置信度
#                 license_num, conf = get_license_result(ocr, each)
#
#                 # 如果识别出车牌号
#                 if license_num:
#                     # 如果车牌号中包含'-', 则移除它
#                     if '-' in license_num:
#                         license_name = license_num.replace('-', '')
#                     # 将识别出的车牌号添加到结果列表中
#                     lisence_res.append(license_num)
#                     # 将识别置信度添加到置信度列表中
#                     conf_list.append(conf)
#
#                     if license_num in plate_count:
#                         plate_count[license_num] += 1
#                     else:
#                         plate_count[license_num] = 1
#                     frame_counter += 1
#                 # 如果未识别出车牌号
#                 else:
#                     # 将'无法识别'添加到结果列表中
#                     lisence_res.append('无法识别')
#                     # 将0添加到置信度列表中，表示未识别
#                     conf_list.append(0)
#             if frame_counter == 30:
#                 # 找到出现次数最多的车牌
#                 most_common_plate, count = max(plate_count.items(), key=lambda x: x[1])
#
#                 # 写入数据库逻辑
#                 insert_parking_info(most_common_plate,"3:20","c")
#
#                 # 重置计数器和计数字典
#                 frame_counter = 0
#                 plate_count = {}
#             # 遍历识别结果列表和图片中车牌的位置列表，为每个车牌绘制识别结果的矩形框
#             for text, box in zip(lisence_res, location_list):
#                 # 使用工具函数在视频帧上绘制矩形框，并显示识别结果
#                 frame = tools.drawRectBox(frame, box, text, fontC)
#
#         # frame = cv2.resize(frame, dsize=None, fx=0.5, fy=0.5, interpolation=cv2.INTER_LINEAR)
#         cv2.imshow("YOLOv8 Detection", frame)
#
#         # Break the loop if 'q' is pressed
#         if cv2.waitKey(1) & 0xFF == ord("q"):
#             break
#     else:
#         # Break the loop if the end of the video is reached
#         break
#
# # Release the video capture object and close the display window
# cap.release()
# cv2.destroyAllWindows()