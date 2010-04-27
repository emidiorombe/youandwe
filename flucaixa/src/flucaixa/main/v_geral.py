from django.shortcuts import render_to_response


def main(request):
    user_ip = request.META["REMOTE_ADDR"]

    return render_to_response("index.html", locals())
