'''
Created on Oct 6, 2010

@author: Rafael Nunes
'''
from main_app.models import Tweet
from datetime import datetime
def json_to_object(json_data):
    tw = Tweet()
    tw.img_url = json_data['profile_image_url'];
    tw.text = json_data['text'];
    tw.from_user = json_data['from_user']
    tw.tweet_id = json_data['id']
    tw.dt_tweet = datetime.strptime(json_data['created_at'][0:24], "%a, %d %b %Y %H:%M:%S")
    return tw

def create_tweet_list(json_data):
    return [json_to_object(data) for data in json_data['results']]
    
