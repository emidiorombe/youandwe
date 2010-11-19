'''
Created on Nov 12, 2010

@author: Rafael Nunes
'''

from domain.models import Tag

def split_tags_into_models(tags):
    tag_list = []
    for tokken in tags.split(','):
        tag = Tag()
        tag.name = tokken.lower()
        tag_list += tag
    return tag_list
