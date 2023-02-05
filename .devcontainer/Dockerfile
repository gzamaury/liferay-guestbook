ARG VARIANT=8
FROM mcr.microsoft.com/devcontainers/java:1-${VARIANT}

# [Optional] Uncomment this section to install additional OS packages.
# RUN apt-get update && export DEBIAN_FRONTEND=noninteractive \
#     && apt-get -y install --no-install-recommends <your-package-list-here>

# [Optional] Uncomment if you want to install more global node modules
# RUN su node -c "npm install -g <your-package-list-here>"

# Persist bash history for a non-root user
# https://code.visualstudio.com/remote/advancedcontainers/persist-bash-history
ARG USERNAME=vscode
RUN SNIPPET="export PROMPT_COMMAND='history -a' && export HISTFILE=/commandhistory/.bash_history" \
    && mkdir /commandhistory \
    && touch /commandhistory/.bash_history \
    && chown -R $USERNAME /commandhistory \
    && echo $SNIPPET >> "/home/$USERNAME/.bashrc"

# Avoiding VS Code extension reinstalls for a non-root user
# https://code.visualstudio.com/remote/advancedcontainers/avoid-extension-reinstalls
RUN mkdir -p /home/$USERNAME/.vscode-server/extensions \
        /home/$USERNAME/.vscode-server-insiders/extensions \
    && chown -R $USERNAME \
        /home/$USERNAME/.vscode-server \
        /home/$USERNAME/.vscode-server-insiders

# Sharing local Liferay repository
RUN mkdir -p /home/$USERNAME/.liferay \
    && chown -R $USERNAME \
        /home/$USERNAME/.liferay

# Sharing local Gradle repository
RUN mkdir -p /home/$USERNAME/.gradle \
    && chown -R $USERNAME \
        /home/$USERNAME/.gradle