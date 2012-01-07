class HomeController < ApplicationController
  def index
    if !current_user
      redirect_to "/login" # force login before displaying instructions
    end
  end

end
