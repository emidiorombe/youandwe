'''
Created on Oct 7, 2010

@author: rafael
'''
from django.shortcuts import render_to_response
from django.http import HttpResponse
from google.appengine.api import memcache, urlfetch
from django.utils import simplejson as json

from main_app.utils import custom_serializer, generic
from main_app.models import Tweet


def get_tweets(request):
    load_tweets_web()
    tweets = load_tweets_base(0, generic.DEFAULT_PAGING)
    memcache.set("tweets", tweets)
    return HttpResponse()

def load_tweets_web():
    url = "http://search.twitter.com/search.json?q=%23gddbr"
    result = urlfetch.fetch(url)
    data = json.loads(result.content)
    tweets = custom_serializer.create_tweet_list(data)
    for tw in tweets:
        tw_db = Tweet.all().filter("tweet_id = ", tw.tweet_id).fetch(1)
        if tw_db.__len__() == 0:
            tw.put()
    return tweets

def load_tweets_base(init, end):
    tweets = Tweet.all().order("-dt_tweet").fetch(end, init)
    return tweets
    
    