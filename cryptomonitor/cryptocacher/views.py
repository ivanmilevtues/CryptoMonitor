from django.shortcuts import render
from django.http import HttpResponse

from cryptocacher.WebSocketInitialization import WebSocketInitialization
# Create your views here.

# TODO: this should be removed and called on the first websocket creation from client
def start_server_websocket(request):
    ws = WebSocketInitialization()
    ws.subscribe()
    return HttpResponse('<h1>Done</h1>')


def page(request):
    return render(request, "index.html")