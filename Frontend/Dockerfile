FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
# O Next lê BACKEND_URL no build (o proxy /api é gravado na compilação)
ARG BACKEND_URL=http://backend:8080
ENV BACKEND_URL=$BACKEND_URL
RUN npm run build

FROM node:20-alpine
WORKDIR /app
ENV NODE_ENV=production
COPY --from=build /app/.next/standalone ./
COPY --from=build /app/.next/static ./.next/static
EXPOSE 3000
CMD ["node", "server.js"]
