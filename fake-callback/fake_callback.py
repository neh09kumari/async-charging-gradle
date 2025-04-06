from http.server import BaseHTTPRequestHandler, HTTPServer

class Handler(BaseHTTPRequestHandler):
    def do_POST(self):
        content_length = int(self.headers.get('Content-Length', 0))
        body = self.rfile.read(content_length)
        print(f"\n Received POST to {self.path}:\n{body.decode('utf-8')}", flush=True)
        self.send_response(200)
        self.end_headers()

server = HTTPServer(('localhost', 9000), Handler)
print("📡 Listening on http://localhost:9000")
server.serve_forever()
