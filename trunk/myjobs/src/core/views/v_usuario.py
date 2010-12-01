'''
Created on Nov 17, 2010

@author: Rafael Nunes
'''
from django.shortcuts import render_to_response, redirect
from django.contrib import auth
from google.appengine.api import memcache, users
from core.forms import UsuarioForm
from core.models import Portfolio
from core.views.decorators import login_required

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
        return render_to_response('user_view.html', locals())

def login(request):
    logged_user = users.get_current_user()
    request.session['logged_user'] = logged_user
    return render_to_response('index.html', locals())  

def logout(request):
    del request.session['logged_user']
    auth.logout(request)
    return redirect('/')
    
