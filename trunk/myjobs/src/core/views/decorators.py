'''
Created on Nov 20, 2010

@author: Rafael Nunes
'''
from django.shortcuts import render_to_response
import logging as log

"""Decorators"""

from django.http import HttpResponseRedirect

def login_required(function):
    """ Implementation of Django's login_required decorator.
  
        The login redirect URL is always set to /login
    """
    def login_required_wrapper(request, *args, **kw):
            if request.user.is_authenticated():
                return function(request, *args, **kw)
            else:
                log.info("====>Not Authenticated")
                return HttpResponseRedirect("/")
    return login_required_wrapper