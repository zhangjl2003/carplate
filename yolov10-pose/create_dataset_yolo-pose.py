import shutil
import cv2
import os


def txt_translate(path, txt_path):
    for filename in os.listdir(path):
        print(filename)

        list1 = filename.split("-", 5)  # 第一次分割，以减号'-'做分割
        subname = list1[2]
        point4 = list1[3]
        print(
            list1[4]
        )
        q = list1[4].split("_")
        print(len(q))
        if len(q) == 8:
            cls = 0
        else:
            cls = 1

        print(cls)
        rd, ld, lu, ru = point4.split("_", 4)
        rdx, rdy = rd.split("&", 1)
        ldx, ldy = ld.split("&", 1)
        lux, luy = lu.split("&", 1)
        rux, ruy = ru.split("&", 1)

        list2 = filename.split(".", 1)
        subname1 = list2[1]
        print(subname1)
        if subname1 == 'txt':
            continue
        lt, rb = subname.split("_", 1)  # 第二次分割，以下划线'_'做分割
        lx, ly = lt.split("&", 1)
        rx, ry = rb.split("&", 1)
        width = int(rx) - int(lx)
        height = int(ry) - int(ly)  # bounding box的宽和高
        cx = float(lx) + width / 2
        cy = float(ly) + height / 2  # bounding box中心点

        img = cv2.imread(path + filename)
        # img = cv2.imread(path+"04451171875-90_247-182&391_569&507-569&507_217&482_182&391_542&397-0_0_5_24_30_31_32_33-112-256.jpg")
        # cv2.imshow("img",img)
        # cv2.waitKey(0)
        # print(type(img))
        # print(img.shape[0])
        if img is None:  # 自动删除失效图片（下载过程有的图片会存在无法读取的情况）

            print("error!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
            os.remove(os.path.join(path, filename))
            continue
        width = width / img.shape[1]
        height = height / img.shape[0]
        cx = cx / img.shape[1]
        cy = cy / img.shape[0]
        rdx = int(rdx) / img.shape[1]
        rdy = int(rdy) / img.shape[0]
        ldx = int(ldx) / img.shape[1]
        ldy = int(ldy) / img.shape[0]
        lux = int(lux) / img.shape[1]
        luy = int(luy) / img.shape[0]
        rux = int(rux) / img.shape[1]
        ruy = int(ruy) / img.shape[0]
        if rdx>1 or rdy>1 or ldx>1 or ldy>1 or lux>1 or luy>1 or rux>1 or ruy>1:
            os.remove(os.path.join(path, filename))
            continue
        txtname = filename.split(".", 1)
        txtfile = txt_path + txtname[0] + ".txt"
        # 绿牌是第0类，蓝牌是第1类
        with open(txtfile, "w") as f:
            f.write(str(cls) + " " + str(cx) + " " + str(cy) + " " + str(width) + " " + str(height)+
                    " "+str(rdx)+" "+str(rdy)+" "+str(ldx)+" "+str(ldy)+" "+str(lux)+" "+str(luy)+" "+str(rux)+" "+str(ruy))


if __name__ == '__main__':
    # det图片存储地址
    trainDir = "datasets//ccpd_green//images//train//"
    validDir = "datasets//ccpd_green//images//val//"
    testDir = "datasets//ccpd_green//images//test//"
    # det txt存储地址
    train_txt_path = "datasets//ccpd_green//labels//train//"
    val_txt_path = "datasets//ccpd_green//labels//val//"
    test_txt_path = "datasets//ccpd_green//labels//test//"
    # txt_translate(trainDir, train_txt_path)
    txt_translate(validDir, val_txt_path)
    # txt_translate(testDir, test_txt_path)
#