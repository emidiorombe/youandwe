'''
Created on Nov 12, 2010

@author: Rafael Nunes
'''
from django.shortcuts import render_to_response
from django.template.context import RequestContext

def split_tags_into_models(tags):
    return 'tag_list'

def secure_render_response(request, template, params):
    return render_to_response(template, params, context_instance=RequestContext(request))