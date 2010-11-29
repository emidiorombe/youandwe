'''
Created on Nov 17, 2010

@author: Rafael Nunes
'''

from google.appengine.ext.db import djangoforms
from domain.models import PortfolioEntry, Usuario


class PortfolioEntryForm(djangoforms.ModelForm):
    class Meta:
        model = PortfolioEntry
        exclude = ['image', 'tags']
        
class UsuarioForm(djangoforms.ModelForm):
    class Meta:
        model = Usuario
        exclude = ['user', 'type', 'creation_date']