from appengine_django.models import BaseModel
from google.appengine.ext import db, blobstore

class PortfolioEntry(BaseModel):
    description = db.StringProperty()
    image = blobstore.BlobReferenceProperty()
    tags = db.StringListProperty()
    
    
class Usuario(BaseModel):
    user = db.UserProperty()
    type = db.IntegerProperty()
    creation_date = db.DateTimeProperty()


class Portfolio(BaseModel):
    entries = db.ReferenceProperty(PortfolioEntry)
    user = db.ReferenceProperty(Usuario)
    
    def get_latest(self):
        return Portfolio.all().fetch(100);
    
