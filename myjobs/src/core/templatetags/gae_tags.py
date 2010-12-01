'''
Created on Nov 18, 2010

@author: Rafael Nunes
'''
from django import template
from google.appengine.api import users
from google.appengine.api.blobstore import blobstore

register = template.Library()

@register.simple_tag
def logout_url():
    return users.create_logout_url('/user/logout')

@register.simple_tag
def portfolio_upload_action():
    return blobstore.create_upload_url("/portfolio/add/")
