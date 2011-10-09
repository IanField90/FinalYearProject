class PasswordResetsController < ApplicationController
  def new
  end
  
  def create
    user = User.find_by_email(params[:email])
    user.send_password_reset if user
    #always display sent message so it is unknown whether an email is taken or not
    redirect_to root_url, :notice => t('password_resets.sent')
  end
  
  def edit
    #! raises 404 if user token isn't found
    @user = User.find_by_password_reset_token!(params[:id])
  end
  
  def update
    @user = User.find_by_password_reset_token!(params[:id])
    if @user.password_reset_sent_at < 2.hours.ago
      redirect_to new_password_reset_path, alert => t('password_resets.expired')
    elsif
      redirect_to root_url, :notice => t('password_resets.reset')
    else
      render :edit
    end
  end

end
