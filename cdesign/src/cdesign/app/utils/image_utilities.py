from cdesign.general_conf import * 
import Image

def resize_image(names):
    for name in names:
        img = Image.open(TMP_PHOTO_DIR.__add__(name))
        imgGrd = img.resize((IMG_GRD_WIDTH, IMG_GRD_HEIGHT), Image.ANTIALIAS)
        imgMed = img.resize((IMG_MED_WIDTH, IMG_MED_HEIGHT), Image.ANTIALIAS)
        imgPeq = img.resize((IMG_PEQ_WIDTH, IMG_PEQ_HEIGHT), Image.ANTIALIAS)
        imgGrd.save(TMP_PHOTO_DIR.__add__("GRD_" + name))
        imgMed.save(TMP_PHOTO_DIR.__add__("MED_" + name)) 
        imgPeq.save(TMP_PHOTO_DIR.__add__("PEQ_" + name))

