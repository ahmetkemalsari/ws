#!/bin/bash

check_docker() {
  if ! command -v docker info &> /dev/null; then
    echo "Docker is required please install Docker.";
    exit 1;
  fi

  if ! docker info > /dev/null 2>&1; then
    echo "This script uses docker, and it isn't running - please start docker and try again!";
    exit 1;
  fi
}

check_docker

VM_NEXUS_REPOSITORY="aksari/ws"

echo -e "Image name: (example: v1.1.1, default: hxbackend)"
read IMAGE_NAME
if [ -z "$IMAGE_NAME" ]; then
  IMAGE_NAME="hxbackend"
fi

echo -e "Tag name: (example: v1.1.1, default: latest)"
read IMAGE_TAG
if [ -z "$IMAGE_TAG" ]; then
  IMAGE_TAG="latest"
fi

echo -e "Do you want push the image to $VM_NEXUS_REPOSITORY repository? (y:n default)"
read -n1 PUSH
if [ "$PUSH" == "y" ]; then
  IMAGE=$VM_NEXUS_REPOSITORY/$IMAGE_NAME:$IMAGE_TAG
  PUSH_ENABLED=true
else
  IMAGE=$IMAGE_NAME:$IMAGE_TAG
  PUSH_ENABLED=false
fi

echo -e "PUSH_ENABLED: $PUSH_ENABLED"
echo -e "IMAGE: $IMAGE"

echo -e 'Old image delete started!'
docker rmi $IMAGE
echo -e 'Old image deleted!'

echo -e 'Project build started!'
chmod 777 pom.xml
pom.xml build --no-daemon -x test
echo -e 'Image builded!'

echo -e 'Docker image build started!'
docker build . -f Dockerfilenobuild -t $IMAGE
if [ $? -eq 0 ]; then
  echo -e 'Docker image builded.'
else
  echo -e 'Docker image build failed.' >&2
  exit 1;
fi

if [ "$PUSH_ENABLED" = true ]; then
  docker push $IMAGE
  if [ $? -eq 0 ]; then
    echo -e 'Image pushed.'
  else
    echo -e 'Image push failed.' >&2
    exit 1;
  fi
fi
