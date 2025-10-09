# frontend.Dockerfile (dev)
FROM node:20

WORKDIR /frontend

# copy package files only to install deps (caching)
COPY frontend/science-calc/package.json frontend/science-calc/package-lock.json ./

# install deps
RUN npm ci

COPY frontend/science-calc/ .
# create user (optional)
# RUN useradd -ms /bin/bash nodeuser && chown -R nodeuser:nodeuser /frontend
# USER nodeuser

# Expose vite default port
EXPOSE 5173

# default command (overridden by compose if needed)
CMD ["npm", "run", "dev"]
