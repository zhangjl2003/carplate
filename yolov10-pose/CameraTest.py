#coding:utf-8
# import cv2
# from ultralytics import YOLO
# import detect_tools as tools
# # from PIL import ImageFont
# from paddleocr import PaddleOCR
import pymysql
import requests
import json

# 假设你的Java后端服务运行在本地的8080端口，且"/parking/parkingInfo/insert"是新增停车信息的API路径
url = "http://localhost:8081/parking/feeScale/insert"
# 准备要发送的数据，根据实际需要填写
new_parking_info = {
    "carNumber": "浙A12555",
    "entryTime": "2023-04-01T10:00:00",
    "feeId": "b",  # 假定的收费标准ID
    # 其他字段根据实际情况填写
}
def read_cookies_file(filename):
 with open(filename, 'r') as fp:
  cookies = fp.read()
  return cookies
cookies_dict =read_cookies_file("cookie.txt")
# headers = {
#  'Accept-Language': 'zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7',
#  'Connection': 'keep-alive',
#  'Cookie': cookies_dict,
#  'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_2) AppleWebKit/537.36 '
#                '(KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36'
# }
# 发送POST请求
response = requests.post(url)

# 检查响应状态码
if response.status_code == 200:
    # 解析响应内容，这里假设后端返回的是JSON格式的数据
    result = response.json()
    print("操作成功，响应数据:", result)
else:
    print(f"操作失败，状态码: {response.status_code}, 错误信息: {response.text}")

# config = {
#     'host': 'localhost',  # 例如 '127.0.0.1' 或 'localhost'
#     'user': 'root',  # 数据库用户名
#     'password': 'zjlzjl',  # 数据库密码
#     'database': 's005',  # 要连接的数据库名称
#     'charset': 'utf8mb4',  # 字符集，推荐使用utf8mb4以支持emoji等字符
# }
# try:
#     con = pymysql.connect(**config)
#     print("数据库成功连接")
# except pymysql.Error as e:
#     print("数据库连接失败")
#
# cur = con.cursor()
#
#
# def insert():
#     # 插入数据一
#     # cur.execute("INSERT INTO movie(title,url,rate) VALUES('"+str(title)+"','"+str(url)+"',"+str(rate)+")")
#     # 插入数据二
#     sql = "INSERT INTO a_parking_data(car_number) VALUES(%s)"
#     values = ("鲁BABCDE")
#     cur.execute(sql,values)
#     # 提交到数据库执行
#     con.commit()
# insert()
#
# cur.execute('select * from a_parking_data')
# results = cur.fetchall()
# # for row in results:
# #     Id = row[0]
# #     title = row[1]
# #     print("id=%s,title=%s" % (Id, title))
# print(results)

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
# def insert(title, url, rate):
#     # 插入数据一
#     # cur.execute("INSERT INTO movie(title,url,rate) VALUES('"+str(title)+"','"+str(url)+"',"+str(rate)+")")
#     # 插入数据二
#     sql = "INSERT INTO movie(title,url,rate) VALUES('" + str(title) + "','" + str(url) + "'," + str(rate) + ")"
#     cur.execute(sql)
#     # 提交到数据库执行
#     con.commit()
#
#
# fontC = ImageFont.truetype("Font/platech.ttf", 50, 0)
# # 加载ocr模型
# cls_model_dir = 'paddleModels/whl/cls/ch_ppocr_mobile_v2.0_cls_infer'
# rec_model_dir = 'paddleModels/whl/rec/ch_PP-OCRv3_rec_infer'
# ocr = PaddleOCR(use_angle_cls=False, lang="ch", det=False, cls_model_dir=cls_model_dir,rec_model_dir=rec_model_dir)
#
# # 所需加载的模型目录
# path = 'train22/weights/best.pt'
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
#         # Run YOLOv8 inference on the frame
#         results = model(frame)[0]
#
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
#             if frame_counter == 50:
#                 # 找到出现次数最多的车牌
#                 most_common_plate, count = max(plate_count.items(), key=lambda x: x[1])
#
#                 # 写入数据库逻辑
#                 insert_into_database(most_common_plate)
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