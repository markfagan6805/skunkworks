# Step 1: Build Maven project
mvn clean install package

# Check if Maven build was successful
if ($LastExitCode -ne 0) {
    Write-Host "Maven build failed. Exiting."
    exit 1
} else {
    Write-Host "Maven build Passed."
}

$imageName = "myapp"
# Get container IDs based on the image name
$containerIds = docker ps -q -a --filter "ancestor=$imageName"

# Stop the containers
foreach ($containerId in $containerIds) {
    Write-Host "Stopping container $containerId..."
    docker stop $containerId
}

# Step 2: Build Docker image
docker build -t $imageName .

# Check if Docker image build was successful
if ($LastExitCode -ne 0) {
    Write-Host "Docker image build failed. Exiting."
    exit 1
} else {
    Write-Host "Docker image build passed"
}

# Step 3: Run Docker container
docker run -d -p 8080:8080 $imageName

# Check if Docker container started successfully
if ($LastExitCode -ne 0) {
    Write-Host "Failed to run Docker container. Exiting."
    exit 1
}

Write-Host "Docker container started successfully."
