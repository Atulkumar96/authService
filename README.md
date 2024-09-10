# authService
Authentication Service/UserService has 'APIs' related to "Auth, Roles &amp; Validate". Authentication Service provides Token/JWT to clients/Third party apps or services for a session or expiry dateTime. Auth Service utilizes "OAuth 2.0, JWTs, BCrypt for Password Encryption". 

# Process of fetching Token from Auth Service 
Client/Third party apps/services talks to -> Authorization Server -> Resource Owner/user allows or denies permission -> If allowed: Auth Server gives the Token to Client.
Client/3rd party apps or services reach Resource Service (i.e. Gmail Server) with Token to fetch data.
Finally, Resource Server validates the Token with the Auth Serveice - if the client has the permission then provides the data (i.e. user info, mail access)

 i.e. Third Party Apps/other Services      - Cred, Amazon, Product Service
      Authorization Service                - Google OAuth Service, authService
      Resource Owner                       - User/we
      Resource Service                     - gmail service, fb, users info providing service

# JWT Token 
has (Header (algo used - HSA 256)
    .Payload (base64 encoded)
    .Signature (algo applied on Header+Payload+Secret key))
    
By AuthService - set-cookie: In Browser Tokens are generally stored in 'Application > Cookies >session_something' 
During consecutive requests, Tokens are Sent to service/server in 'Authorization - header'
Any service before serving the requested resource/request, it validates the JWT with Auth Service

Tokens are like the Passports to Applications

/logout requests go to Auth Service & the Auth Service deletes the token from db/cache

# Bcrypt
Signup: Stores as encrpypt(pwd + salting) = encrpyted value
Login: Given a pwd & an encrpted string, Bcrypt_check (pwd ~ encrypted value) - Match then Loggedin
