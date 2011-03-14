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
    return settings.CDN_URL


