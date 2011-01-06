'''
Created on Jan 6, 2011

@author: Rafael Nunes
'''
from django.shortcuts import render_to_response

def index(request):
    return render_to_response('index.xhtml', locals())
