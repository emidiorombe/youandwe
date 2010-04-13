from django.forms import ModelForm
from django import forms
from cdesign.app.models import User


class NewUserForm(ModelForm):
    class Meta:
        model = User
    
    def clean_state(self):
        data = self.cleaned_data['state']
        #raise forms.ValidationError("You are not in Sao Paulo")
        return data
    
class UploadFileForm(forms.Form):
    description = forms.CharField(widget=forms.Textarea)
    file = forms.FileField()