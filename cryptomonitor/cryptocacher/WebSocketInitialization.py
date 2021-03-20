import asyncio
import json
import websockets
import decimal

from datetime import datetime
from cryptocacher.models import CryptoCurrency
from django.apps import AppConfig

from channels.db import database_sync_to_async


class WebSocketInitialization():

    def subscribe(self):
        with open("keys.json") as json_file:
            data = json.load(json_file)
            self.api_key = data['CRYPTO_COMPARE_API_KEY']

        asyncio.run(self._async_subscibe())

    async def _async_subscibe(self):
        url = "wss://streamer.cryptocompare.com/v2?api_key=" + self.api_key
        async with websockets.connect(url) as websocket:
            await websocket.send(json.dumps({
                "action": "SubAdd",
                "subs": ["0~Coinbase~BTC~USD"],
            }))
            while True:
                try:
                    data = await websocket.recv()
                except websockets.ConnectionClosed:
                    break
                try:
                    data = json.loads(data)
                    print(data)
                    await self._save_to_database(data)
                except (ValueError, KeyError) as _:
                    print(data)
        

    @database_sync_to_async
    def _save_to_database(self, data):
        print(data)
        CryptoCurrency.objects.create(
            market=data['M'],
            crypto_currency_name=data['FSYM'],
            money_currency_name=data['TSYM'],
            price = decimal.Decimal(data['P']),
            transaction_time=datetime.fromtimestamp(int(data['TS']))
        ).save()
