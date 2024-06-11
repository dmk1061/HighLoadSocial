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

#systemctl stop postgresql@14-main.service
#systemctl stop redis-server.service
