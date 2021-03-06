class ApplicationController < ActionController::Base
  protect_from_forgery
  
  #Used to be used for locales
  # before_filter :set_locale
  # 
  # def set_locale
  #   I18n.locale = params[:locale] || I18n.default_locale
  # end
  # 
  # def default_url_options(options={})
  #   logger.debug "default_url_options is passed options: #{options.inspect}\n"
  #   { :locale => I18n.locale }
  # end
  
  private
  def current_user
    @current_user ||= User.find_by_auth_token!(cookies[:auth_token]) if cookies[:auth_token]
  end
  helper_method :current_user
  
  def is_user_admin
    current_user && current_user.admin
  end
  helper_method :is_user_admin
end
