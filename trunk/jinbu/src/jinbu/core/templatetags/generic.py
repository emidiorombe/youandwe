'''
Created on Mar 14, 2011

@author: Rafel nunes
'''
from jinbu import settings
from django import template

register = template.Library()

@register.simple_tag
def BUSTER():
    return settings.BUSTER_VERSION

@register.simple_tag
def CDN():
    if settings.DEBUG:
        return settings.CDN_DEV_URL
    else:
        return settings.CDN_URL


