from django.shortcuts import render_to_response

def index(request):
    myvar = 'Olaaaaaaa enfermeira!'
    return render_to_response('index.html', locals())

def erro404(request):
    return render_to_response('404.html')