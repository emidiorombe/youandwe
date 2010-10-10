from django.shortcuts import render_to_response
from django.utils.translation import gettext as msg
from google.appengine.api import memcache, urlfetch
from django.utils import simplejson as json
from datetime import datetime
from main_app.utils import custom_serializer 
from main_app.views import v_infra
from main_app.models import *



def index(request):
    return render_to_response('index.html', locals())
   
def list(request):
    #Carrega os tweets na ordem > Cache - Banco - HTTP
    tweets = memcache.get("tweets")
    if tweets is None :
        tweets = v_infra.load_tweets_base()
        if tweets is None or tweets.__len__() == 0:
            tweets = v_infra.load_tweets_web()
        memcache.set("tweets", tweets)
   
    #Carrega os comentarios de cada tweet
    all_comments = {}
    for tw in tweets:
        all_comments[tw.tweet_id] = Comentario.all().filter("tweet =", tw).fetch(100)
    return render_to_response('list_tw.html', locals())
    
def adicionar_comentario(request):
    if request.method == 'POST':
        cm = Comentario()
        cm.text = request.POST['txt_c']
        tw = request.POST['id_tw']
        cm.pub_date = datetime.now()
        cm.tweet = db.get(tw)
        cm.put()
    return render_to_response('list_tw.html', locals())

def erro404(request):
    return render_to_response('404.html')

def erro500(request):
    return render_to_response('500.html')

