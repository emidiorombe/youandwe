from django.shortcuts import render_to_response
from django.utils.translation import gettext as msg
from google.appengine.api import memcache, users

from core.models import Portfolio, PortfolioEntry

def index(request):
    plist = memcache.get('list_p')
    if users.get_current_user():
        logged_user = users.get_current_user()
    else:
        login_url = users.create_login_url('/user/login')
    if plist is None:
        portfolio = Portfolio()
        plist = portfolio.get_latest()
    return render_to_response('index.html', locals())
   
    

def erro404(request):
    return render_to_response('404.html')

def erro500(request):
    return render_to_response('500.html')