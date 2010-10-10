from appengine_django.models import BaseModel
from google.appengine.ext import db

class Tweet(BaseModel):
    text = db.StringProperty(multiline=True)
    img_url = db.StringProperty()
    from_user = db.StringProperty()
    tweet_id = db.IntegerProperty()
    dt_tweet = db.DateTimeProperty()
    
class Comentario(BaseModel):
    text = db.StringProperty(multiline=True)
    user = db.StringProperty()
    tweet = db.ReferenceProperty(Tweet)
    pub_date = db.DateTimeProperty()
    