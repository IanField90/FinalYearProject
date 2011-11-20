class CoursesController < ApplicationController
  def index
  end

  def create
    # current_user is nill if no-one is logged in, check for that, then if they are admin
    if current_user && current_user.admin
      render :create
    else
      redirect_to courses_path
    end
  end

  def update
  end

  def destroy
  end

end
