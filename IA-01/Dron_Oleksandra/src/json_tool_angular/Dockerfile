FROM node:alpine AS builder
WORKDIR /app/fe
COPY . .
RUN npm ci && npm run build
FROM nginx:alpine
COPY --from=builder /app/fe/dist/json_tool_angular /usr/share/nginx/html
EXPOSE 80
