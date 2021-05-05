import pickle
from os.path import dirname, join

def impVec():
    venSen = join(dirname(__file__), "vecSen.pickle")
    inVec = open(venSen,"rb")
    vec = pickle.load(inVec)
    inVec.close()
    return vec

def impMod():
    modelSen = join(dirname(__file__), "modelSen.pickle")
    inVec = open(modelSen,"rb")
    vec = pickle.load(inVec)
    inVec.close()
    return vec

def PST(text):
    vec = impVec()
    text = text.lower()
    text = text.strip()
    text = [text]
    text = vec.transform(text)
    
    return text