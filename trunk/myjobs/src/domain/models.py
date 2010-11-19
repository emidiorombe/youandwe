from appengine_django.models import BaseModel
from google.appengine.ext import db, blobstore


class Tag(BaseModel):
    name = db.StringProperty()
    
class PortfolioEntry(BaseModel):
    image_description = db.StringProperty()
    image = blobstore.BlobReferenceProperty()
    title = db.StringProperty()
    tags = db.ListProperty(db.Key)
    creation_date = db.DateTimeProperty(auto_now_add=True)
    
    
class Usuario(BaseModel):
    user = db.UserProperty()
    type = db.IntegerProperty() #pagante ou free
    perfil = db.IntegerProperty() #user or company
    name = db.StringProperty()
    url = db.URLProperty()
    mail = db.EmailProperty()
    image_description = db.TextProperty()
    creation_date = db.DateTimeProperty(auto_now_add=True)


class Portfolio(BaseModel):
    entries = db.ListProperty(db.Key)
    user = db.ReferenceProperty(Usuario)
    description = db.TextProperty()
    
    def get_latest(self):
        return Portfolio.all().fetch(100);
    
