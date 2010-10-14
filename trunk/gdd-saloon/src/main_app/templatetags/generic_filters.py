'''
Created on Oct 14, 2010

@author: rafael
'''
from django import template

register = template.Library()

@register.filter(name='paging')
def paging(value, arg):
    if value == '':
        return ''
    elif arg == '-':
        return value - 1
    elif arg == '+':
        return value + 1

@register.filter(name='trying')    
def trying(value, arg1):  
    return type(arg1)