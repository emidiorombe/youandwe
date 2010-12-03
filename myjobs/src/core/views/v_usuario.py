'''
Created on Nov 17, 2010

@author: Rafael Nunes
'''
import logging as log
from django.shortcuts import render_to_response, redirect
from django.contrib import auth
from google.appengine.api import memcache, users
from core.forms import UsuarioForm
from core.models import Portfolio
from core.views.decorators import login_required
from django.contrib.auth import authenticate, login, logout
from django.core.context_processors import csrf
from django.template.context import RequestContext
from django.views.decorators.csrf import csrf_protect
from core.utils.utils import secure_render_response

@login_required
def create_user(request):
    if request.method == 'GET':
        return render_to_response('user_edit.html', locals())
    elif request.method == 'POST':
        user_form = UsuarioForm(request.POST)
        if user_form.is_valid():
            user_new = user_form.save(commit=False)
            user_new.type = 1
            portfolio = Portfolio()
            portfolio.save()
            user_new.portfolio = portfolio
            user_new.put()
        return render_to_response('user_view.html',  locals())


@csrf_protect
def login_user(request):
    if request.method == 'GET':
        return secure_render_response(request, 'login.html', locals())
    elif request.method == 'POST':
        user = authenticate(username=request.POST['txtMail'], password=request.POST['txtPwd'])
        if user is not None:
            login(request, user)
            return render_to_response('index.html', locals())
        else:
            return render_to_response('login.html', locals())  

def logout_user(request):
    logout(request)
    return redirect('/')
    
