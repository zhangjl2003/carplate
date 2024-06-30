from ultralytics import YOLO

# Load a model
model = YOLO("yolov10n-pose.yaml", task='pose')  # build a new model from YAML

# Train the model
results = model.train(data="carplate.yaml", epochs=100, imgsz=640,workers=0,batch=8)


# yolo task=pose mode=train model=./yolov8s-pose.pt data="C:\Users\zjl\Desktop\课程\软件工程概论\ultralytics-maine.yaml" workers=4 epochs=50 batch=12
