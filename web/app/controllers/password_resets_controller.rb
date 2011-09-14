class PasswordResetsController < ApplicationController
  def new
  end
  
  def create
    user = User.find_by_email(params[:email])
    user.send_password_reset if user
    #always display sent message so it is unknown whether an email is taken or not
    redirect_to root_url, :notice => "Email sent with password reset instructions."
  end
  
  def edit
    #! raises 404 if user token isn't found
    @user = User.find_by_password_reset_token!(params[:id])
  end
  
  def update
    @user = User.find_by_password_reset_token!(params[:id])
    if @user.password_reset_sent_at < 2.hours.ago
      redirect_to new_password_reset_path, alert => "Password reset has expired"
    elsif
      redirect_to root_url, :notice => "Password has been reset!"
    else
      render :edit
    end
  end

end
