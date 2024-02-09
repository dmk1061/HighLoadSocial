volumes=$(docker volume ls -q)

# Loop through each volume
for volume in $volumes; do
    # Get the mountpoint of the volume
    mountpoint=$(docker volume inspect --format '{{ .Mountpoint }}' $volume)

    # Check if the mountpoint exists
    if [ ! -d "$mountpoint" ]; then
        echo "Deleting volume: $volume"
        docker volume rm $volume
    fi
done