'''
Created on Nov 12, 2010

@author: Rafael Nunes
'''
from google.appengine.ext.webapp import blobstore_handlers
def get_blobkeys(request):
    pass

class UpHa(blobstore_handlers.BlobstoreUploadHandler):
    def x(self):
        self.get_uploads('field_name')
