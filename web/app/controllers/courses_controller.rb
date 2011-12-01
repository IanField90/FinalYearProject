class CoursesController < ApplicationController

  def index
  end

  def new
    # current_user is nill if no-one is logged in, check for that, then if they are admin
    @course = Course.new
    if current_user && current_user.admin
      render :new
    else
      redirect_to courses_path
    end
  end

  def update
  end

  def destroy
  end

end
