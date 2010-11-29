'''
Created on Nov 18, 2010

@author: Rafael Nunes
'''
from django import template
from google.appengine.api import users

register = template.Library()

@register.simple_tag
def logout_url():
    return users.create_logout_url('/user/logout')
