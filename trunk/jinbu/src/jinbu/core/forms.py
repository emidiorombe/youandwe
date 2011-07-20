'''
Created on Jan 6, 2011

@author: Rafael Nunes
'''
from django.forms.models import ModelForm
from jinbu.core.models import Empresa

class EmpresaForm(ModelForm):
    class Meta:
        model = Empresa
        fields = ('first_name', 'fone', 'site', 'email', 'username')