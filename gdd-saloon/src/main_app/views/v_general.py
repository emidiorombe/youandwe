from django.shortcuts import render_to_response
from django.http import HttpResponse, HttpResponseRedirect
from django.utils.translation import gettext as msg
from django.utils import simplejson as json
from google.appengine.api import memcache, urlfetch
from google.appengine.api import users
from datetime import datetime

from main_app.utils import custom_serializer, generic 
from main_app.views import v_infra
from main_app.models import *



def index(request):
    login_url = users.create_login_url("/list")
    var = (1,2,3)
    return render_to_response('index.html', locals())

def list(request, pg=1):
    pg_number = 1 if pg == '' else int(pg)
    user = users.get_current_user()
    if user is None:
        return HttpResponseRedirect("/")
    
    #Se nao estiver paginando, busca do cache
    tweets = None
    if pg_number <= 1:
        tweets = memcache.get("tweets")
        if tweets is None :
            tweets = v_infra.load_tweets_base(0, generic.DEFAULT_PAGING)
            page_next = pg_number
    else:
        init, end, page = generic.get_paging(pg_number)
        page_next = page
        tweets = v_infra.load_tweets_base(init, end)
   
    #Carrega os comentarios de cada tweet
    all_comments = {}
    for tw in tweets:
        all_comments[tw.tweet_id] = Comentario.all().filter("tweet =", tw).fetch(1000)
    return render_to_response('list_tw.html', locals())
    
def adicionar_comentario(request):
    user = users.get_current_user()
    if request.method == 'POST':
        cm = Comentario()
        cm.text = request.POST['txt_c']
        tw = request.POST['id_tw']
        cm.pub_date = datetime.now()
        cm.tweet = db.get(tw)
        cm.user = user
        cm.put()
    return HttpResponse()

def search_tweet(request):
    user = users.get_current_user()
    tw_id = request.POST['tw_id_search']
    tweets = Tweet.all().filter("tweet_id = ", int(tw_id)).order('-tweet_id').fetch(100)
    #Carrega os comentarios de cada tweet
    all_comments = {}
    for tw in tweets:
        if tw.is_saved():
            all_comments[tw.tweet_id] = Comentario.all().filter("tweet =", tw).fetch(1000)
    return render_to_response('list_tw.html', locals())

def delete_all(request):
    tweets = Tweet.all().fetch(400)
    db.delete(tweets)
    cm = Comentario.all().fetch(50)
    db.delete(cm)
    return HttpResponse()

def erro404(request):
    return render_to_response('404.html')

def erro500(request):
    return render_to_response('500.html')

