'''
Created on Nov 17, 2010

@author: Rafael Nunes
'''

from django import forms
from core.models import PortfolioEntry, Usuario


class PortfolioEntryForm(forms.ModelForm):
    class Meta:
        model = PortfolioEntry
        exclude = ['image', 'tags', 'portfolio', 'creation_date']
        
class UsuarioForm(forms.ModelForm):
    class Meta:
        model = Usuario
        exclude = ['djuser', 'type', 'portfolio']