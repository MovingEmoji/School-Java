#Naoya iida
from socketserver import ThreadingTCPServer, BaseRequestHandler
from http.server import SimpleHTTPRequestHandler
import os

class CustomHTTPRequestHandler(SimpleHTTPRequestHandler):
    def log_message(self, format, *args):
        # このメソッドをオーバーライドして、ログ出力を無効化
        return

    def do_GET(self):
        # ルートディレクトリ設定
        root = os.getcwd()
        
        requested_path = self.path
        if requested_path == '/':
            requested_path = '/index.html'  # デフォルトページ

        # ファイルのフルパスを取得
        file_path = root + requested_path

        # ファイルの存在チェック
        if not os.path.isfile(file_path):
            self.send_error(404, "File not found")
            return

        # ファイルを開いて内容を送信
        try:
            with open(file_path, 'rb') as file:
                self.send_response(200)
                self.end_headers()
                self.wfile.write(file.read())
        except IOError:
            self.send_error(500, "File read error")

if __name__ == '__main__':
    server_address = ('', 8080)
    ThreadingTCPServer.allow_reuse_address = True
    print("Server running on port 8080...")
    with ThreadingTCPServer(server_address, CustomHTTPRequestHandler) as server:
        server.serve_forever()


# This is ApacheBench, Version 2.3 <$Revision: 1879490 $>
# Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
# Licensed to The Apache Software Foundation, http://www.apache.org/

# Benchmarking localhost (be patient)
# Completed 100 requests
# Completed 200 requests
# Completed 300 requests
# Completed 400 requests
# Completed 500 requests
# Completed 600 requests
# Completed 700 requests
# Completed 800 requests
# Completed 900 requests
# Completed 1000 requests
# Finished 1000 requests


# Server Software:        SimpleHTTP/0.6
# Server Hostname:        localhost
# Server Port:            8080

# Document Path:          /img.jpg
# Document Length:        502212 bytes

# Concurrency Level:      5
# Time taken for tests:   2.682 seconds
# Complete requests:      1000
# Failed requests:        0
# Total transferred:      502306000 bytes
# HTML transferred:       502212000 bytes
# Requests per second:    372.92 [#/sec] (mean)
# Time per request:       13.408 [ms] (mean)
# Time per request:       2.682 [ms] (mean, across all concurrent requests)
# Transfer rate:          182929.46 [Kbytes/sec] received

# Connection Times (ms)
#               min  mean[+/-sd] median   max
# Connect:        0    0   0.2      0       3
# Processing:     0    3   4.5      2      19
# Waiting:        0    2   3.5      1      17
# Total:          0    3   4.5      3      19

# Percentage of the requests served within a certain time (ms)
#   50%      3
#   66%      3
#   75%      3
#   80%      4
#   90%     13
#   95%     16
#   98%     16
#   99%     16
#  100%     19 (longest request)