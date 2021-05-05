# -*- coding: utf-8 -*-
"""
Created on Wed May  5 12:54:56 2021

@author: kohai
"""

from ProcessInitiation import PST, impMod
def pred(text):
    #Import the model from preprocessing
    model = impMod()
    #Convert the text into a vectorized string
    text = PST(text)
    res = model.predict(text)
    
    #The data comes out as a array. Convert into a int
    res = res[0]
    
    return res

res = pred("awful")
print(res)