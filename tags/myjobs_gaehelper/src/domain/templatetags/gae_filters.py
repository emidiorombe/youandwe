'''
Created on Nov 12, 2010

@author: Rafael Nunes
'''

from django import template
from google.appengine.api.blobstore import blobstore

register = template.Library()

@register.filter(name='blob_upload_url')
def paging(value):
    return blobstore.create_upload_url(value)

